#!/usr/bin/env bash
#
# Prints this repository's SonarQube project key to stdout.
#
# Projects imported through the SonarQube GitHub App are assigned a generated
# key of the form "<org>_<repo>_<uuid>". The uuid cannot be derived from any
# GitHub context, so the key is looked up through the SonarQube API instead of
# being assumed from a naming convention.
#
# On any problem this prints nothing and exits 0. Callers must treat an empty
# result as "skip analysis": letting the scanner run with a guessed key would
# silently create a second, unbound project instead of reporting an error.
set -uo pipefail

log() { printf '%s\n' "$*" >&2; }

if [ -z "${SONAR_HOST_URL:-}" ]; then
  log "SONAR_HOST_URL is not set."
  exit 0
fi

if [ -z "${SONAR_TOKEN:-}" ]; then
  log "SONAR_TOKEN is not set."
  exit 0
fi

case "$SONAR_HOST_URL" in
  http://*|https://*) ;;
  *)
    log "SONAR_HOST_URL must include a scheme, e.g. https://sonarqube.example.com"
    exit 0
    ;;
esac

HOST="${SONAR_HOST_URL%/}"
OWNER="${GITHUB_REPOSITORY%%/*}"
REPO="${GITHUB_REPOSITORY#*/}"
PREFIX="${OWNER}_${REPO}"

RESPONSE="$(curl -sS -u "${SONAR_TOKEN}:" --get \
  --data-urlencode "q=${REPO}" \
  --data-urlencode "qualifiers=TRK" \
  --data-urlencode "ps=500" \
  "${HOST}/api/components/search")" || {
    log "Could not query ${HOST}/api/components/search."
    exit 0
  }

if printf '%s' "$RESPONSE" | jq -e 'has("errors")' >/dev/null 2>&1; then
  log "SonarQube returned an error: $(printf '%s' "$RESPONSE" | jq -r '[.errors[].msg] | join("; ")')"
  exit 0
fi

# Preference order: exact "<org>_<repo>", then "<org>_<repo>_<uuid>" as created
# by the GitHub App import, then a project whose display name is the repo name,
# then a bare "<repo>" key.
KEY="$(printf '%s' "$RESPONSE" | jq -r --arg prefix "$PREFIX" --arg repo "$REPO" '
  [.components[]?] as $c
  | (
      [ $c[] | select(.key == $prefix) | .key ]
      + [ $c[]
          | select((.key | startswith($prefix + "_"))
                   and ((.key | length) == (($prefix | length) + 37)))
          | .key ]
      + [ $c[] | select(.name == $repo) | .key ]
      + [ $c[] | select(.key == $repo) | .key ]
    )
  | first // empty
' 2>/dev/null)"

if [ -z "$KEY" ]; then
  log "No SonarQube project matching '${REPO}' was found. Has it been imported?"
  exit 0
fi

log "Resolved SonarQube project key: ${KEY}"
printf '%s\n' "$KEY"

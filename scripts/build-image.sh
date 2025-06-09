#!/bin/bash

set -eo pipefail

export BUNDLE_ASSETS="true"

if [[ "$PUBLISH_OFFICIAL" == "true" ]]; then
    BACKEND_PUBLISH="publish"
else
    BACKEND_PUBLISH="publishLocal"
fi

set -u

# build frontend so it's available to be bundled
(cd modules/frontend; npm i && npm run build)

# build backend w/ default dependencies
publish_backend="backend / Docker / $BACKEND_PUBLISH"
sbt "$publish_backend"

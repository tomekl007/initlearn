#!/usr/bin/env bash

#create account
curl -X POST \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -d '{
        "email": "endToEndTest@gmail.com",
        "givenName": "End",
        "grant_type": "password",
        "password": "changeMeQuickly",
        "surname": "ToEnd"
    }' \
"https://initlearn.herokuapp.com/registerAccount"

#login to application
curl -X POST --user 48IT3XS1FWL80VX1PDYQV3SDF:6j+AfVnxEijenM5gq6WBbO9e6dvw2kE60zMKlIfB2hM \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=endToEndTest@gmail.com&password=changeMeQuickly&grant_type=password" \
"https://api.stormpath.com/v1/applications/1Lst6onrAoaDCXqrhcvQTc/oauth/token"

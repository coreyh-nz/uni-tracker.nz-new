#!/bin/bash
set -e

openssl genrsa -out private_key.pem 2048 > /dev/null 2>&1

# private key -> PKCS#8 DER -> base64
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt 2>/dev/null
PRIVATE_B64=$(cat private_key.der | base64 | tr -d '\n')

# public key -> SPKI DER -> base64
openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der 2>/dev/null
PUBLIC_B64=$(cat public_key.der | base64 | tr -d '\n')

# cleanup temp files
rm -f private_key.pem private_key.der public_key.der

echo "JWT_PRIVATE_KEY=$PRIVATE_B64"
echo
echo "JWT_PUBLIC_KEY=$PUBLIC_B64"
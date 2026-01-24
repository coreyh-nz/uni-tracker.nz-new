# Uni-Tracker Auth Service

This service handles authentication for Uni-Tracker.

## Environment Variables

The auth service requires several environment variables to run. You can create a .env file in the auth-service directory
or set them in your shell.

### Required Variables

| Variable          | Description                                               |
|-------------------|-----------------------------------------------------------|
| `JWT_PRIVATE_KEY` | Base64-encoded private key for signing JWTs (see below)   |
| `JWT_PUBLIC_KEY`  | Base64-encoded public key for verifying JWTs              |
| `JWT_ISSUER`      | The issuer of the JWTs (service name or URL)              |
| `JWT_AUDIENCE`    | The intended audience for the JWTs (client service names) |

---

## Running the Service

### Generating JWT Keys

A script is provided to generate the JWT keys in the correct format. It will output the base64-encoded private and
public keys that can be set as environment variables.

#### Run the script:

```bash
cd auth-service/scripts
chmod +x generate-jwt-keys.sh
./generate-jwt-keys.sh
```

The script will generate and print a private and public key. Copy these values into your .env file along with the issuer
and audience:

```
JWT_PRIVATE_KEY=<paste-private-key-here>
JWT_PUBLIC_KEY=<paste-public-key-here>
JWT_ISSUER=uni-tracker-auth-service
JWT_AUDIENCE=uni-tracker-api
```
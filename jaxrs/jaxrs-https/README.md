# JAX-RS over HTTPS Example

This example is identical to JAX-RS,
with the addition of setting up HTTPS endpoint on
port 8443 in parallel with the HTTP endpoint on 8080.

## Keystore

The `keystore.jks` is secured with the password 'password'
and so is the certificate.  The certificate is self-signed,
so may generate insecurity warnings in your browser.

The certificate's alias is `selfsigned`.

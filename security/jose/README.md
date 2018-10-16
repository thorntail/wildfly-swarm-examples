# JOSE Example

This example demonstrates Thorntail Jose which offers a complete support for Javascript Object Signing and Encryption.

The demo server emulates a Bank resource accepting the approved or rejected loan applications in the following JSON format:

{"approvalStatus":"APPROVED","signature":"..."}

or

{"approvalStatus":"REJECTED","signature":"..."}

where the signature field represents a JOSE JWS compact signature of either 'APPROVED' or 'REJECTED' status values with the values themselves not being included in the signature sequence. The values, using the JOSE terminology, are detached.

The demo server verifies the signatures and echoes back the status values if the signatures are valid or returns HTTP 401 otherwise which is tested during the demo build by the integration test which uses a Jose4J library to create the client requests.

You can also use any other 3rd party JOSE library to test the interoperability between it and the Thorntail Jose demo server.

Here we will show you how to use a Fedora 'jose' package

## Install Fedora 'jose' package

``` sh
dnf install jose
```

and use it to sign both 'APPROVED' and 'REJECTED' values using the JOSE JWK symmetric key shipped with the demo:

``` sh
echo -n APPROVED | jose jws sig -I- -c -O /dev/null -k src/main/resources/jwk.keys
echo -n REJECTED | jose jws sig -I- -c -O /dev/null -k src/main/resources/jwk.keys
```

## Run the demo server

Now run the server and pass the 'APPROVED' and 'REJECTED' signatures as 'approvedJws' and 'rejectedJws' properties respectively:

``` sh
mvn thorntail:run -DapprovedJws=... -DrejectedJws=...

```

## Run curl

``` sh
curl -X POST -H "Content-Type: application/json" -d @src/target/classes/approvedStatus.json
curl -X POST -H "Content-Type: application/json" -d @src/target/classes/rejectedStatus.json
```

You will get 'APPROVED' and 'REJECTED' echoed back.

Finally run

``` sh
curl -v -X POST -H "Content-Type: application/json" -d @src/target/classes/rejectedApprovedStatus.json
```

You will get HTTP 401. This request emulates a case where the content has been manipulated after it has been signed, in this case, the signed 'REJECTED' content has been replaced with 'APPROVED'. 




 



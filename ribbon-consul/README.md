# Multiple services plus NetFlixOSS Ribbon

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

This example is like the `ribbon` example, but instead of using
JGroups, it relies upon Hashicorp's Consul service-discovery mechanisms.

To execute this test, you must

* Have a Consul server/agent running on http://<IP>:<PORT>

You can run consul with

    $ consul agent -dev

Once the above is satisfied, you can run the examples with:

    $ mvn wildfly-swarm:run -Dswarm.bind.address=127.0.0.1 -Dswarm.consul.url=http://<IP>:<PORT> -Dswarm.port.offset=<OFFSET>
 
 You need to supply a bind address since wildfly-swarm will bind to "0.0.0.0" by default, which consul doesn't accept as service endpoint.
 If you have consul running on localhost you can leave out the `swarm.consul.url`.
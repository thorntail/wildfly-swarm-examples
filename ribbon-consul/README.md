# Multiple services plus NetFlixOSS Ribbon

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

This example is like the `ribbon` example, but instead of using
JGroups, it relies upon Hashicorp's Consul service-discovery mechanisms.

To execute this test, you must

* Have a Consul server/agent running on http://<IP>:<PORT>

You can run consul with

    consul agent -dev

Once the above is satisfied, you can run

    mvn verify -Pconsul -Dswarm.consul.url=http://<IP>:<PORT>



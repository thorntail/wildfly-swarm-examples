# Multiple services plus NetFlixOSS Ribbon

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Services

Two services exist:

* Time
* Events

The `time` service simply returns the current time as a JSON map
with fields for hour, minute, second, etc.

The `events` service queries the `time` service, and returns a list of
currently on-going events.

Each of these services may be accessed with a simple HTTP `GET` request.
For example, the `time` service:

    $ curl http://127.0.0.1:8081
    {"s":5,"D":3,"ms":647,"tz":"America/New_York","h":10,"Y":2015,"M":12,"m":37}

In addition, the `event` service will accept an HTTP `POST` request with JSON data
specifying the event type. Every `GET` or `POST` request to the `event` service
generates a new event.

## Front End

There is a simple JAX-RS front end that uses the `ribbon-webapp` fraction to
communicate with the services. The web site displays the current Ribbon topology,
and provides a simple button-based UI to `GET` or `POST` messages to the Ribbon
services.

## Try it Out

First, start up the front end, so you can watch the Ribbon topology get updated
in real time. Open a terminal window.

    $ cd frontend
    $ mvn thorntail:run

Then open a browser to `http://127.0.0.1:8080`. There will be nothing to see there
yet. Leave this window open and visible while you bring up the two services.

Open another terminal window to build and run the time service:

    $ cd time
    $ mvn -Dswarm.port.offset=1 thorntail:run

You should see the Ribbon topology update in the browser as the time service
comes up. Now, open another terminal window, and run the `time` service again,
but this time give it a different port number. Notice how the web UI updates
itself as the service comes up.

    $ cd time
    $ mvn -Dswarm.port.offset=2 thorntail:run

Finally, open yet another terminal window and run the events service,
which consumes the time service(s). Again, note the UI changes.

    $ cd events
    $ mvn -Dswarm.port.offset=3 thorntail:run

Now you can kill and restart one or both of the `time` services, and witness the
UI changes. You can also `GET` time and event service data, and `POST` new events.

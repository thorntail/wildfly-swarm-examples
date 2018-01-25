topology().then(function(topo) {
  var App = React.createClass({
    render: function() {
      return (
          <div>
            <h1>WildFly Swarm Ribbon Example</h1>
            <Topology />
          </div>
      );
    }
  });

  var Topology = React.createClass({
    getInitialState: function() {
      return {data: []};
    },

    componentDidMount: function() {
      var component = this;
      topo.onTopologyChange(function(topology) {
        component.setState({data: topology});
      });
    },

    render: function() {
      var services = [];
      for (var service in this.state.data) {
        var addresses = this.state.data[service];
        services.push(
          (
              <Service service={service} addresses={addresses} key={service} />
          )
        );
      }
      return (
        <div id='topology'>
          <h2>Service Topology</h2>
          {services}
        </div>
      );
    }
  });

  var Service = React.createClass({
    render: function() {
      var addresses = this.props.addresses.map(function(address) {
        return (
            <Address address={address} key={address.endpoint}/>
        );
      });

      if (this.props.service === 'time') {
        return (
          <div className='service'>
            <TimeService endpoints={addresses} />
          </div>
        );
      } if ( this.props.service == 'events' ) {
        return (
          <div className='service'>
            <EventService endpoints={addresses} />
          </div>
        );
      } else {
        return (
          <div className='service'>
            <OtherService service={this.props.service} endpoints={addresses} />
          </div>
        )
      }
    }
  });

  function formatTime(obj) {
    return obj['h'] + ':' + obj['m'] + ':' + obj['s'] + '.' + obj['ms'] + ' on ' +
      obj['Y'] + '-' + obj['M'] + '-' + obj['D'] + ' ';
  }

  var TimeService = React.createClass({
    getInitialState: function() {
      return {responses: []};
    },

    updatePanel: function(response) {
      console.log(response);
      this.setState({
        responses: this.state.responses.concat([response])
      });
    },

    render: function() {
      return (
        <div className='time-service'>
          <h2>Time Service</h2>
          <div className='endpoints'>
          <h3>Endpoints</h3>
          {this.props.endpoints}
          </div>
          <GetJSONButton serviceName='time' responseHandler={this.updatePanel} />
          {this.state.responses.reverse().map(function(response) {
            return (
                <TimeStamp key={response['ms']} timestamp={response} />
            );
          })}
        </div>
      );
    }
  });

  var TimeStamp = React.createClass({
    render: function() {
      return (
        <div className='timestamp'>
          {formatTime(this.props.timestamp)}
        </div>
      );
    }
  });

  var EventService = React.createClass({
    getInitialState: function() {
      return {response: []};
    },

    updatePanel: function(response) {
      console.log(response);
      this.setState({
        response: response
      });
    },

    render: function() {
      return (
        <div className='event-service'>
          <h2>Event Service</h2>
          <div className='endpoints'>
          <h3>Endpoints</h3>
          {this.props.endpoints}
          </div>
          <GetJSONButton serviceName='events' responseHandler={this.updatePanel} />
          <PostJSONButton serviceName='events' responseHandler={this.updatePanel} />

          {this.state.response.reverse().map(function(evt) {
            return (
                <Event key={evt.id} event={evt} />
            );
          })}

        </div>
      );
    }
  });

  var Event = React.createClass({
    render: function() {
      return (
        <ul className='event'>
          <li>Event ID: {this.props.event.id}</li>
          <li>Event Type: {this.props.event.name}</li>
          <li>Timestamp: <TimeStamp key={'e' + this.props.event.timestamp['ms']}
            timestamp={this.props.event.timestamp} /></li>
        </ul>
      );
    }
  });

  var OtherService = React.createClass({
    getInitialState: function() {
      return {response: []};
    },

    updatePanel: function(response) {
      console.log(response);
      this.setState({
        response: response
      });
    },

    render: function() {
      return (
        <div className='other-service'>
          <h2>{this.props.service} Service</h2>
          <div className='endpoints'>
          <h3>Endpoints</h3>
          {this.props.endpoints}
          </div>
        </div>
      );
    }
  });

  var GetJSONButton = React.createClass({
    handleClick: function(event) {
      topo.getJSON(this.props.serviceName)
        .then(this.props.responseHandler);
    },

    render: function() {
      return (
        <div>
          <p onClick={this.handleClick}>
          <span className='btn get-btn'>
            Click to GET {this.props.serviceName}
          </span>
          </p>
        </div>
      );
    }
  });

  var PostJSONButton = React.createClass({
    handleClick: function(event) {
      topo.postJSON(this.props.serviceName, {name: 'User POST'})
        .then(this.props.responseHandler);
    },

    render: function() {
      return (
          <div>
          <p onClick={this.handleClick}>
          <span className='btn post-btn'>
          Click to post a new item to {this.props.serviceName}
        </span>
          </p>
          </div>
      );
    }
  });

  var Address = React.createClass({
    render: function() {
      return (
        <div>
          <p className='service-address'>{this.props.address.endpoint}</p>
          {this.props.address.tags.map( function(tag) {
            return (<span key={tag}>{tag}</span>);
          })}
        </div>
      );
    }
  });

  ReactDOM.render(
    <App />,
    document.getElementById('app')
  );
});

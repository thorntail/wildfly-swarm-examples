
var keycloak = new Keycloak( '/browser-client-keycloak.json' );


var App = React.createClass({
  componentWillMount: function() {
    var self = this;
    this.props.keycloak.init({ onLoad: 'check-sso' }).success( function() {
      if ( self.props.keycloak.authenticated ) {
        self.setState( {keycloak: self.props.keycloak} );
      }
    } );
  },

  render: function() {
    return (
        <div>
          <h1>Thorntail Keycloak Example</h1>
          <Auth keycloak={this.props.keycloak}/>
          <Secured keycloak={this.props.keycloak}/>
        </div>
    );
  }
});

var Auth = React.createClass({
  render: function() {
    console.log( "render", this.props.keycloak );
    if ( this.props.keycloak.authenticated ) {
      return (
        <div><Logout keycloak={this.props.keycloak}/></div>
      );
    }

    return (
      <div><Login keycloak={this.props.keycloak}/></div>
    )
  }
});

var Secured = React.createClass({
  handleClick: function() {
    var req = new XMLHttpRequest();
    req.open('GET', './app/secured', true);
    req.setRequestHeader('Authorization', 'Bearer ' + this.props.keycloak.token);
    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if (req.status == 200) {
               alert(req.responseText);
            } else {
               alert('Unauthorized');
            }
        }
    }
    req.send();
  },

  render: function() {
    return (
      <div id="securedService" onClick={this.handleClick}>
        Access Secured Service
      </div>
    )
  }
});


var Login = React.createClass({
  handleClick: function() {
    this.props.keycloak.login();
  },

  render: function() {
    return (
      <div id="login" onClick={this.handleClick}>
        Login
      </div>
    )
  }
})

var Logout = React.createClass({
  handleClick: function() {
    this.props.keycloak.logout();
  },

  render: function() {
    return (
      <div id="logout" onClick={this.handleClick}>
        Logout
      </div>
    )
  }
})

ReactDOM.render(
  <App keycloak={keycloak}/>,
  document.getElementById('app')
);


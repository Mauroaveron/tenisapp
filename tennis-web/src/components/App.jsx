import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Wrapper from './Wrapper/Wrapper';
import Home from '../containers/Home/Home';
import Jugador from '../containers/Jugador/Jugador';
import Detalle from '../containers/Jugador/Detalle';
import Entrenador from '../containers/Entrenador/Entrenador';
import Partido from '../containers/Partido/Partido';
import JugarPartido from '../containers/JugarPartido/JugarPartido';
import Cancha from '../containers/Cancha/Cancha';
import About from '../containers/About/About';
import NotFound from '../containers/NotFound/NotFound';

class App extends React.Component {
  render() {
    return (
      <>
        <BrowserRouter>
          <Wrapper>
            <Switch>
              <Route exact path="/" component={Home} />
              <Route exact path="/jugador" component={Jugador} />
              <Route exact path="/jugador/detalle/:id" component={Detalle} />
              <Route exact path="/partido" component={Partido} />
              <Route exact path="/entrenador" component={Entrenador} />
              <Route exact path="/partido/jugar-partido" component={JugarPartido} />
              <Route exact path="/cancha" component={Cancha} />
              <Route exact path="/about" component={About} />
              <Route component={NotFound} />
            </Switch>
          </Wrapper>
        </BrowserRouter>
      </>
    );
  }
}

export default App;
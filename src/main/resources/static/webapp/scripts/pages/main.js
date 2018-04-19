import React, {Component} from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import Interfaces from './interfaces';
import Properties from "./properties";



class Main extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <BrowserRouter>
            <Switch>
                <Route exact path="/" component={Interfaces} />
                <Route exact path="/interface/:id" component={Properties} />

            </Switch>
        </BrowserRouter>;
    }
}

export default Main;

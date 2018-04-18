import React, {Component} from 'react';
import {Container, Tab, Header} from 'semantic-ui-react';
import Mapping from './mapping';
import Configuration from './configuration';
import Connection from './connection';
import AuditLog from './auditlog';
import Interfaces from './interfaces';

const panes = [
    {
        menuItem: 'AuditLog', render: () =>
            <Tab.Pane>
                <AuditLog/>
            </Tab.Pane>
    },
    {
        menuItem: 'Mapping', render: () =>
        <Tab.Pane>
            <Mapping/>
        </Tab.Pane>
    },
    {
        menuItem: 'Configuration', render: () =>
        <Tab.Pane>
            <Configuration/>
        </Tab.Pane>
    },
    {
        menuItem: 'Connection', render: () =>
        <Tab.Pane>
            <Connection/>
        </Tab.Pane>
    }
];

class Main extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Container style={{height: "100%", width: "100%"}}>
            <Interfaces />
           {/*<Tab style={{height: "100%"}} menu={{fluid: true, vertical: true, tabular: 'right'}} panes={panes}/>*/}
        </Container>;
    }
}

export default Main;

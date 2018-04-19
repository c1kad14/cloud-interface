import React, {Component} from 'react';
import {Container, Tab, Header} from 'semantic-ui-react';
import Mapping from './mapping';
import Configuration from './configuration';
import Connection from './connection';
import AuditLog from './auditlog';



class Properties extends Component {
    constructor(props) {
        super(props);
    }

    getConfiguration(id) {
        this.setState({interfaceId: id});
    }

    componentWillMount() {
        this.getConfiguration(this.props.match.params.id);
    }
    componentWillReceiveProps(nextProps) {
        this.getConfiguration(nextProps.match.params.id);
    }

    render() {

        const id = this.state.interfaceId;
        const panes = [
            {
                menuItem: 'AuditLog', render: () =>
                    <Tab.Pane>
                        <AuditLog interfaceId={id}/>
                    </Tab.Pane>
            },
            {
                menuItem: 'Mapping', render: () =>
                    <Tab.Pane>
                        <Mapping interfaceId={id}/>
                    </Tab.Pane>
            },
            {
                menuItem: 'Configuration', render: () =>
                    <Tab.Pane>
                        <Configuration interfaceId={id}/>
                    </Tab.Pane>
            },
            {
                menuItem: 'Connection', render: () =>
                    <Tab.Pane>
                        <Connection interfaceId={id}/>
                    </Tab.Pane>
            }
        ];

        return <Container style={{height: "100%", width: "100%"}}>
            <Header>{this.props.interface}</Header>
            {<Tab style={{height: "100%"}} menu={{fluid: true, vertical: true, tabular: 'right'}} panes={panes}/>}
        </Container>;
    }
}

export default Properties;

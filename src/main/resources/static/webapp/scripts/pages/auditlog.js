import React, { Component } from 'react';
import {Container, Header,  Divider, Input, Icon, Form, Label } from 'semantic-ui-react';
import TextProperty from "../components/TextProperty";

class AuditLog extends Component{
    constructor(props) {
        super(props);
        this.state = {
        }
    }

    componentWillMount() {

    }

    render() {
        const that = this;
        return <Container>
            <Header as='h3'>Audit Log</Header>
            <Container>

            </Container>
        </Container>;
    }

}

export default AuditLog;
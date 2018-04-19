import React, {Component} from 'react';
import {Container, Header, Input, Label, Divider} from 'semantic-ui-react';

class Connection extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Container>
            <Header as='h3'>Connection</Header>
            <Divider />
            <Container>
                <Label>Username: </Label><Input palceholder="api username"/>
                <br/>
                <Label>Password: </Label><Input palceholder="api password"/>
            </Container>
        </Container>;
    }
}

export default Connection;
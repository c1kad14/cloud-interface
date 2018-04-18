import React, {Component} from 'react';
import {Container, Header} from 'semantic-ui-react';

class Connection extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Container>
            <Header as='h3'>Connection</Header>
            <Container>
                Connection Content
            </Container>
        </Container>;
    }
}

export default Connection;
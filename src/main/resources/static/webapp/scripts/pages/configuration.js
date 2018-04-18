import React, { Component } from 'react';
import {Container, Header,  Divider, Input, Icon, Form, Label } from 'semantic-ui-react';
import TextProperty from "../components/TextProperty";

class Configuration extends Component{
    constructor(props) {
        super(props);
        this.state = {
            importFolder: ""
        }
    }

    componentWillMount() {

    }

    render() {
        const that = this;
        return <Container>
            <Header as='h3'>Configuration</Header>
            <Container>
                <Form>
                    <Form.Field>
                        <TextProperty icon="folder open"
                                      error="Please enter path to the pickup folder"
                                      placeholder="Import folder"
                                      value={this.state.importFolder}
                                      onChange={(e) => {that.setState({importFolder: e.target.value})}}
                        />
                    </Form.Field>
                    <Divider />
                </Form>
            </Container>
        </Container>;
    }

}

export default Configuration;
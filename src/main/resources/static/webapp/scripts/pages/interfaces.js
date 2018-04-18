import React, {Component} from 'react';
import {Container, Form, Header, Divider, Button, Icon, Table} from 'semantic-ui-react';
import TextProperty from "../components/TextProperty";
import axios from 'axios';
import Querystring from 'query-string';



class Interfaces extends Component {
    constructor(props) {
        super(props);
        this.state = {
            interfaces: [],
            interfaceToAddName: "",
            token: ""
        }
        this.addNewInterfaceClickHandler = this.addNewInterfaceClickHandler.bind(this);
    }

    componentWillMount() {
        const that = this;
      /*  axios.get("api/auth").then((response) => {
            that.setState({interfaceToAddName: response.data.access_token})
            console.log('userresponse ' + response.data);
            })
            .catch((error) => {
            console.log('error ' + error);
        });;*/
    }

    addNewInterfaceClickHandler() {
        if(this.state.interfaceToAddName !== "") {
            axios.post('api/interfaces', {"name" : this.state.interfaceToAddName});
        }
    }

    render() {
        const that = this;
        return <Container>
            <Form>
                <Form.Field>
                    <TextProperty icon="folder open"
                                  error="Please enter unique interface name"
                                  placeholder="Add new interface"
                                  value={this.state.interfaceToAddName}
                                  onChange={(e) => {that.setState({interfaceToAddName: e.target.value})}}
                    />
                </Form.Field>
                <Button content="+" primary onClick={this.addNewInterfaceClickHandler}>
                    <Icon name='add circle' />
                </Button>
                <Form.Field>

                </Form.Field>
                <Divider />
                <Table>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell colSpan="3">List if existing interfaces</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                </Table>
            </Form>
        </Container>;
    }
}

export default Interfaces;

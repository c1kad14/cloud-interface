import React, {Component} from 'react';
import {Container, Form, Button,Header, Divider, Icon, Table, Label, Input} from 'semantic-ui-react';
import axios from 'axios';
import { Link } from "react-router-dom";


class Interfaces extends Component {
    constructor(props) {
        super(props);
        this.state = {
            interfaces: [],
            interfaceToAddName: "",
            token: ""
        }
        this.addNewInterfaceClickHandler = this.addNewInterfaceClickHandler.bind(this);
        this.deleteInterface = this.deleteInterface.bind(this);
    }

    refreshData() {
        const that = this;
        axios.get("api/interfaces/getlist").then((response) => {
            that.setState({interfaces: response.data})
            console.log('userresponse ' + response.data);
        })
        .catch((error) => {
            console.log('error ' + error);
        });
    }

    componentWillMount() {
        this.refreshData();
    }

    addNewInterfaceClickHandler() {
        const that = this;
        if (this.state.interfaceToAddName !== "") {
            axios.post('api/interfaces/add', {"name": this.state.interfaceToAddName}).then(() => {
                that.setState({interfaceToAddName: ""}, that.refreshData());
            });
        }
    }

    deleteInterface(id) {
        const that = this;
        axios.delete('api/interfaces/delete?id=' + id).then(() => {
            that.refreshData();
        });
    }


    render() {
        const that = this;
        const interfaceList = this.state.interfaces.map((existingInterface) => {
            return <Table.Row key={existingInterface.id}>
                <Table.Cell>
                    <Label style={{fontSize:"16px"}}>{existingInterface.name}</Label>
                </Table.Cell>
                <Table.Cell collapsing textAlign='right'>
                    <Link to={`/interface/${existingInterface.id}`}><Button><Icon name="edit"/></Button></Link>
                </Table.Cell>
                <Table.Cell collapsing textAlign='right'>
                    <Button onClick={() => this.deleteInterface(existingInterface.id)}><Icon name="delete"/></Button></Table.Cell>
            </Table.Row>
        });
        return  <Container style={{height: "100%", width: "60%"}}>
            <Container>
                <Form>
                    <Form.Field style={{width: "100%"}}>
                        <Input style={{width: "90%"}}
                                      placeholder="Add new interface"
                                      value={this.state.interfaceToAddName}
                                      onChange={(e) => {
                                          that.setState({interfaceToAddName: e.target.value})
                                      }}
                        />
                        <Button style={{marginLeft: "10px"}}primary onClick={this.addNewInterfaceClickHandler}>ADD</Button>
                    </Form.Field>
                    <Form.Field>

                    </Form.Field>
                    <Divider/>
                    <Table>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell colSpan="3" style={{fontSize:"24px"}}>LIST OF EXISTING INTERFACES</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {interfaceList}
                        </Table.Body>
                    </Table>
                </Form>
            </Container>
        </Container>;
    }
}

export default Interfaces;

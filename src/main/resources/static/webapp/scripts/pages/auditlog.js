import React, { Component } from 'react';
import {Container, Header,  Table, Label } from 'semantic-ui-react';
import TextProperty from "../components/TextProperty";
import axios from "axios/index";

class AuditLog extends Component{
    constructor(props) {
        super(props);
        this.state = {
            logs: []
        }
    }

    refreshData() {
        const that = this;
        axios.get("/api/auditlogs/getlist").then((response) => {
            that.setState({logs: response.data})
        })
        .catch((error) => {
            console.log('error ' + error);
        });
    }

    componentWillMount() {
        this.refreshData();
    }

    render() {
        const logs = this.state.logs.map((log) => {
            return <Table.Row key={log.id}>
                <Table.Cell>
                    <Label>{log.id}</Label>
                </Table.Cell>
                <Table.Cell>
                    <Label>{log.name}</Label>
                </Table.Cell>
                <Table.Cell>
                    <Label>{log.time}</Label>
                </Table.Cell>
                <Table.Cell>
                    <Label>{log.status ? "Success" : "False"}</Label>
                </Table.Cell>
            </Table.Row>
        });
        return <Container style={{width: "100%"}}>
            <Header as='h3'>Audit Log</Header>
            <Container style={{width: "100%"}}>
                <Table style={{width: "100%"}}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Id</Table.HeaderCell>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Time</Table.HeaderCell>
                            <Table.HeaderCell>Success</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {logs}
                    </Table.Body>
                </Table>
            </Container>
        </Container>;
    }

}

export default AuditLog;
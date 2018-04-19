import React, {Component} from 'react';
import {Container, Header, Accordion, Button, Icon, Input, Label, Table} from 'semantic-ui-react';
import axios from 'axios';

const schema = {
    "person": {
        "first": "$.first",
        "last": "$.last",
        "middle": "$.middle",
        "birthDate": "$.birthDate",
        "sex": "$.sex"
    },
    "inmate": {
        "agency": "$.agency",
        "assignedHousing": "$.inmate.assignedHousing",
        "currentBookingId": "$.inmate.currentBookingId",
        "person": {
            "id": "$.person.id",
            "first": "$.person.first",
            "last": "$.person.last",
            "middle": "$.person.middle",
            "birthDate": "$.person.birthDate",
            "sex": "$.person.sex"
        }
    },
    "booking": {
        "bookingAgency": "$.bookingAgency",
        "bookingType": "$.bookingType",
        "inmate": {
            "agency": "$.inmate.agency",
            "person": {
                "first": "$.inmate.person.first",
                "last": "$.inmate.person.last",
                "middle": "$.inmate.person.middle",
                "birthdate": "$.inmate.person.birthdate",
                "sex": "$.inmate.person.sex"
            },
            "assignedHousing": "$.inmate.assignedHousing",
            "currentBookingId": "$.inmate.currentBookingId"
        }
    }
};
const modelNames = ["person", "inmate", "booking"];

class Mapping extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeIndex: 0,
            model: {}
        };
        this.handleClick = this.handleClick.bind(this);
        this.saveFieldMapping = this.saveFieldMapping.bind(this);
        this.saveChildFieldMapping = this.saveChildFieldMapping.bind(this);
        this.saveMapping = this.saveMapping.bind(this);

    }

    handleClick(e, titleProps) {
        const {index} = titleProps
        const {activeIndex} = this.state
        const newIndex = activeIndex === index ? -1 : index;

        this.setState({activeIndex: newIndex })
    }

    componentWillMount() {
        const that = this;
        axios.get("/api/mappings/getlist").then((response) => {
            if (response.data !== undefined && response.data !== null && response.data !== []) {
                const mappings = response.data.filter((mapping) => {
                    return mapping.interfaceId === that.props.interfaceId;
                });

                let model = that.state.model;
                for(let i = 0; i < mappings.length; i++) {
                    const mapping = mappings[i];
                    let elements = mapping.local.split('.')

                    if(elements.length > 2) {
                        if(model[elements[1]] === undefined) {
                            model[elements[1]] = {};
                        }
                        model[elements[1]][elements[2]] = mapping.remote;
                    } else {
                        model[elements[1]] = mapping.remote;
                    }
                }


                that.setState({});
            }
        });
    }

    saveMapping() {
        const that = this;
        let mappings = [];
        for (let key in this.state.model) {
            if (this.state.model.hasOwnProperty(key) && this.state.model[key] !== "") {
                let modelToAdd = { };
                if (typeof(this.state.model[key]) !== "object") {
                    modelToAdd.local = schema[modelNames[this.state.activeIndex]][key];
                    modelToAdd.remote = this.state.model[key];
                    modelToAdd.interfaceId = this.props.interfaceId;
                    mappings.push(modelToAdd);
                } else {
                    for (let childKey in this.state.model[key]) {
                        if (this.state.model[key].hasOwnProperty(childKey)) {
                            modelToAdd = {};
                            modelToAdd.local = schema[modelNames[this.state.activeIndex]][key][childKey];
                            modelToAdd.remote = this.state.model[key][childKey];
                            modelToAdd.interfaceId = this.props.interfaceId;
                            mappings.push(modelToAdd);
                        }
                    }
                }
            }
        }
        mappings.map((mapping) => {
            axios.post("/api/mappings/add", mapping).then((response) => {
            });
        });

    }

    saveFieldMapping(key, value) {
        this.state.model[key] = value;
        this.setState({});
    }

    saveChildFieldMapping(key, childKey, value) {
        this.state.model[key][childKey] = value;
    }

    getTableForModel(modelName) {
        const model = schema[modelName];
        const that = this;
        let tableBody = [];
        for (let key in model) {
            if (model.hasOwnProperty(key)) {
                if (typeof(model[key]) !== "object") {
                    const row = <Table.Row key={key}>
                        <Table.Cell>
                            <Label>{key}</Label>
                        </Table.Cell>
                        <Table.Cell>
                            <Input value={this.state.model[key]} onChange={(e) => that.saveFieldMapping(key, e.target.value)}/>
                        </Table.Cell>
                    </Table.Row>;
                    tableBody.push(row);
                } else {
                    tableBody.push( <Table.Row key={key}>
                        <Table.HeaderCell colSpan="3">{key}</Table.HeaderCell>
                    </Table.Row>);
                    if(this.state.model[key] === undefined) {
                        this.state.model[key] = {};
                    }
                    for (let childKey in model[key]) {
                        if (model[key].hasOwnProperty(childKey)) {
                            const row = <Table.Row key={childKey}>
                                <Table.Cell>
                                    <Label>{childKey}</Label>
                                </Table.Cell>
                                <Table.Cell>
                                    <Input value={this.state.model[key] !== undefined ? this.state.model[key][childKey] : ""} onChange={(e) => that.saveChildFieldMapping(key, childKey, e.target.value)}/>
                                </Table.Cell>
                            </Table.Row>;
                            tableBody.push(row);
                        }
                    }
                }
            }
        }

        return <Container>
            <Table>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>Model Field</Table.HeaderCell>
                        <Table.HeaderCell>XML Tag</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {tableBody}
                </Table.Body>
            </Table>
            <Button onClick={this.saveMapping.bind(this)}>Save</Button>
            </Container>;
    }

    render() {
        const {activeIndex} = this.state
        const data = this.getTableForModel(modelNames[activeIndex]);
        return <Container>
            <Header as='h3'>Mapping</Header>
            <Container>
                <Accordion styled>
                    <Accordion.Title active={activeIndex === 0} index={0} onClick={this.handleClick}>
                        <Icon name='dropdown'/>
                        Person
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 0}>
                        {data}
                    </Accordion.Content>
                    <Accordion.Title active={activeIndex === 1} index={1} onClick={this.handleClick}>
                        <Icon name='dropdown'/>
                        Inmate
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 1}>
                        {data}
                    </Accordion.Content>
                    <Accordion.Title active={activeIndex === 2} index={2} onClick={this.handleClick}>
                        <Icon name='dropdown'/>
                        Booking
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 2}>
                        {data}
                    </Accordion.Content>
                </Accordion>
            </Container>
        </Container>;
    }

}

export default Mapping;
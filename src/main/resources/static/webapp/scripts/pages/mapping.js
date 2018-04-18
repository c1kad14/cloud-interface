import React, {Component} from 'react';
import {Container, Header, Accordion, Icon, Dropdown } from 'semantic-ui-react';




class Mapping extends Component {
    constructor(props) {
        super(props);
        this.state = { activeIndex: 0 }
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(e, titleProps) {
        const { index } = titleProps
        const { activeIndex } = this.state
        const newIndex = activeIndex === index ? -1 : index

        this.setState({ activeIndex: newIndex })
    }

    render() {

        const { activeIndex } = this.state
        return <Container>
            <Header as='h3'>Mapping</Header>
            <Container>
                <Accordion styled>
                    <Accordion.Title active={activeIndex === 0} index={0} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        Person
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 0}>
                        <p>
                          Person model schema here
                        </p>
                    </Accordion.Content>
                    <Accordion.Title active={activeIndex === 1} index={1} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        Inmate
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 1}>
                        <p>
                            Inmate model schema here
                        </p>
                    </Accordion.Content>
                    <Accordion.Title active={activeIndex === 2} index={2} onClick={this.handleClick}>
                        <Icon name='dropdown' />
                        Booking
                    </Accordion.Title>
                    <Accordion.Content active={activeIndex === 2}>
                        <p>
                            Booking model schema here
                        </p>
                    </Accordion.Content>
                </Accordion>
            </Container>
        </Container>;
    }

}

export default Mapping;
import React from 'react';
import {Container, Input, Icon, Label } from 'semantic-ui-react';

const TextProperty = (props) => {
    return <Container>
        <Input type='text' placeholder={props.placeholder} value={props.value} onChange={(e) => props.onChange(e)}/>
        {props.value === "" ? <Label pointing>
            <Icon name={props.icon} />
            {props.error}
        </Label> : <span />}
    </Container>;
}

export default TextProperty;
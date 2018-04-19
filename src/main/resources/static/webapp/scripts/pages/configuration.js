import React, {Component} from 'react';
import {Container, Header, Divider, Button, Input, Icon, Form, Label} from 'semantic-ui-react';
import TextProperty from "../components/TextProperty";
import axios from 'axios';

class Configuration extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fileMask: ""
        }
    }

    componentWillMount() {

    }

    openFileInput() {
        this.fileInput.click();
    }

    fileChange(e) {
        e.preventDefault();
        let files = e.target.files;
        let that = this;
        if (files.length == 1) {
            const file = files[0];
            let reader = new FileReader();
            reader.onloadend = function () {
                if (this.readyState === FileReader.DONE) {
                    const selectedFile = {src: reader.result, file: file};
                    that.setState({fileSource: selectedFile.file});
                }
            }

            reader.readAsDataURL(file);
        }
    }

    parseXml() {
        let formData = new FormData();
        if (this.state.fileSource !== undefined) {
            formData.set("file", this.state.fileSource);
            formData.set("interfaceId", this.props.interfaceId);
        }
        const config = { headers: { 'content-type': 'multipart/form-data' } };
        axios.post('/testxml', formData, config);
    }

    render() {
        const that = this;
        return <Container>
            <Header as='h3'>Configuration</Header>
            <Container>
                <Form>
                    <Form.Field>
                        <Button onClick={this.openFileInput.bind(this)}><Icon name="file text"/></Button>
                            <input
                                ref={(input) => {
                                    this.fileInput = input;
                                }}
                                style={{visibility: "hidden"}}
                                type="file"
                                onChange={(e) => this.fileChange(e)}/>
                        <Button onClick={this.parseXml.bind(this)}>Parse test xml</Button>
                        <TextProperty icon="folder open"
                                      placeholder="File mask"
                                      value={this.state.fileMask}
                                      onChange={(e) => {
                                          that.setState({fileMask: e.target.value})
                                      }}
                        />
                    </Form.Field>
                    <Divider/>
                </Form>
            </Container>
        </Container>;
    }

}

export default Configuration;
import React from "react";
import './Start.css'

export default class StartComponent extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div id='startComp'>
                <div id='description'>
                    <h1>Numeric Analysis</h1>
                    sdjkdfklfkfgsdjkdfklfkfgsdjkdfklfkfg
                    <br/>
                    sdjkdfklfkfgsdjkdfklfkfgsdjkdfklfkfg
                </div>
                <hr/>
                <div id='links'>
                    <a href='/es' className='startLink'>Divide by half method</a>
                    <a href='/es' className='startLink'>Newton method</a>
                    <a href='/sle' className='startLink'>Gaus method</a>
                    <a href='/sle' className='startLink'>Run method</a>
                    <a href='/interpolation' className='startLink'>Lagrange method</a>
                    <a href='/interpolation' className='startLink'>Newton method</a>
                    <a href='/interpolation' className='startLink'>MMS method</a>
                    <a href='/interpolation' className='startLink'>Splains method</a>
                </div>
            </div>);
    }
}

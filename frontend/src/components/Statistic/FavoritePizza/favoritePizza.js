/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from 'react';
import CanvasJSReact from "../../../assets/canvasjs.react";

const FavoritePizza = (props) => {
    let options = {
        title: {
            text: "Favorite pizzas"
        },
        data: [{
            type: "column",
            dataPoints: Object.keys(props.orders).map((order) => {
                return {label: order, y: props.orders[order]}
            }),
        }]
    };
    return (
        <div>
            <CanvasJSReact.CanvasJSChart options={options}/>
        </div>
    );
};

export default FavoritePizza;
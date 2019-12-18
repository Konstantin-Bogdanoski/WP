/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from 'react';
import CanvasJSReact from "../../../assets/canvasjs.react";

const FavoriteHours = (props) => {
    let options = {
        title: {
            text: "Favorite time of day"
        },
        data: [{
            type: "column",
            dataPoints: Object.keys(props.hours).map((hour) => {
                return {label: (hour + ":00 - " + (parseInt(hour) + 1) + ":00"), y: props.hours[hour]}
            }),
        }]
    };
    return (
        <div>
            <CanvasJSReact.CanvasJSChart options={options}/>
        </div>
    );
};

export default FavoriteHours;
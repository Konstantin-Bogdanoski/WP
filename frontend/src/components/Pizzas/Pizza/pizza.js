/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component, useEffect, useState} from "react";
import {Link} from "react-router-dom";
import axios from "../../../custom-axios/axios";

const Pizza = (props) => {
    let [ingredients, setIngredient] = useState();

    useEffect(() => {
        axios.get("/pizzas/" + props.pizza.id + "/ingredients").then((data) => {
            const ingredients = data.data.map((ingredient, index) => {
                return (
                    <li className="list-group" key={index}>{ingredient}</li>
                );
            });
            setIngredient(ingredients);
        });
    }, []);

    const PizzaHeader = () => {
        return (
            <div className="card-header">
                <div className="row">
                    <div className="col-md-6">
                        {props.pizza.name}
                    </div>
                    <div className="col-md-6 text-right">
                        <button href="#" className="btn btn-light" title="Order">
                            <i className="fa fa-star"/>
                        </button>
                        <button className="btn btn-default" to={"/pizzas/" + props.pizza.id + "/edit"}><i
                            className="fa fa-pencil"/>Edit
                        </button>
                        <button onClick={() => props.onDelete(props.pizza.id)} className="btn btn-danger"
                                title="Delete">
                            <i className="fa fa-trash"/>
                        </button>
                    </div>
                </div>
            </div>
        );
    };

    const PizzaBody = () => {
        return (
            <div className="row">
                <div className="col-md-6 font-weight-bold">Ingredients</div>
                <ul className="list-group">
                    {ingredients}
                </ul>
            </div>
        );
    };

    return (
        <div className={props.colClass}>
            <div className="card">
                <div className="pizzas">
                    {PizzaHeader()}
                    {PizzaBody()}
                </div>
            </div>
        </div>
    )
};

export default Pizza;
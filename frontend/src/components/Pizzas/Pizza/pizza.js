/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import axios from "../../../custom-axios/axios";

const Pizza = (props) => {
    let [ingredients, setIngredient] = useState();

    useEffect(() => {
        axios.get("/pizzas/" + props.pizza.id + "/ingredients").then((data) => {
            const ingredients = Object.keys(data.data).map((ingredient) => {
                return (
                    <tr className="col-md-6 mt-2 col-sm-12">
                        <td>
                            {ingredient}
                        </td>
                        <td>
                            {data.data[ingredient]}g
                        </td>
                    </tr>
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
                        <Link to={"/pizzas/" + props.pizza.id + "/edit"}
                              className="btn btn-sm btn-secondary">
                            <span className="fa fa-edit"/>
                            <span><strong>Edit</strong></span>
                        </Link>
                        <Link to={"/pizzas"} onClick={() => {
                            this.props.onDelete(props.pizza.id)
                        }} className="btn btn-sm btn-outline-secondary ">
                            <span className="fa fa-remove"/>
                            <span><strong>Remove</strong></span>
                        </Link>
                    </div>
                </div>
            </div>
        );
    };

    const PizzaBody = () => {
        return (
            <div className="table-responsive">
                <table className="table tr-history table-striped small">
                    <thead>
                    <tr>
                        <th>
                            Ingredient
                        </th>
                        <th>
                            Amount
                        </th>
                    </tr>
                    </thead>
                    {ingredients}
                </table>
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
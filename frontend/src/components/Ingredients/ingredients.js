/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import Ingredient from "./Ingredient/ingredient";
import {Link} from "react-router-dom";


const Ingredients = (props) => {
    const ingredients = props.ingredients.map((ingredient, index) => {
        return (
            <Ingredient ingredient={ingredient} key={index} onDelete={props.onDelete}
                        colClass={"col-md-6 mt-2 col-sm-12"}/>
        );
    });
    return (
        <div className="row">
            <h4 className="text-upper text-center">Ingredients</h4>
            <div className="table-responsive">
                <table className="table tr-history table-striped small">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Spicy</th>
                        <th scope="col">Veggie</th>
                        <th scope="col">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {ingredients}
                    </tbody>
                </table>
            </div>
            <Link className="btn btn-outline-secondary" to={"/ingredients/new"}>
                <span><strong>Add new ingredient</strong></span>
            </Link>
        </div>

    )
};
export default Ingredients;
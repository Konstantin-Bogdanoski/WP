/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import {Link} from "react-router-dom";

class Ingredient extends Component {
    render() {
        return (
            this.renderIngredient()
        );
    }

    renderIngredient() {
        return (
            <tr>
                <td scope="col">{this.props.ingredient.name}</td>
                <td scope="col">{String(this.props.ingredient.spicy)}</td>
                <td scope="col">{String(this.props.ingredient.veggie)}</td>
                <td scope="col">
                    <Link to={"/ingredients/" + this.props.ingredient.id + "/edit"}
                          className="btn btn-sm btn-secondary">
                        <span className="fa fa-edit"/>
                        <span><strong>Edit</strong></span>
                    </Link>
                    <Link to={"/ingredients"} onClick={() => {
                        this.props.onDelete(this.props.ingredient.id)
                    }} className="btn btn-sm btn-outline-secondary ">
                        <span className="fa fa-remove"/>
                        <span><strong>Remove</strong></span>
                    </Link>
                    <Link to={"/ingredients/" + this.props.ingredient.id + "/details"}
                          className="btn btn-sm btn-outline-dark">
                        <span><strong>Details</strong></span>
                    </Link>
                </td>
            </tr>
        )
    }
}

export default Ingredient;
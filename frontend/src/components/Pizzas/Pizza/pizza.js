/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import {Link} from "react-router-dom";

class Pizza extends Component {
    render() {
        return (
            <div className={this.props.colClass}>
                <div className="card">
                    <div className="pizzas">
                        {this.cardHeader()}
                        {this.slotPizza()}
                        <hr/>
                    </div>
                </div>
            </div>
        );
    }

    cardHeader() {
        return (
            <div className="card-header">
                <div className="row">
                    <div className="col-md-6">
                        {this.props.pizza.name}
                    </div>
                    <div className="col-md-6 text-right">
                        <button href="#" className="btn btn-light" title="Order">
                            <i className="fa fa-star"/>
                        </button>
                        <button className="btn btn-default" to={"/pizzas/" + this.props.pizza.id + "/edit"}><i
                            className="fa fa-pencil"/>Edit
                        </button>
                        <button onClick={() => this.props.onDelete(this.props.pizza.id)} className="btn btn-danger"
                                title="Delete">
                            <i className="fa fa-trash"/>
                        </button>
                    </div>

                </div>
            </div>);
    }

    slotPizza() {
        return (
            <div className="row">
                <div className="col-md-6 font-weight-bold">Description</div>
                {this.props.pizza.description}
            </div>
        );
    }
}

export default Pizza;
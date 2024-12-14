// ProductDetails.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ProductDetails = ({ productId }) => {
    const [product, setProduct] = useState({});

    useEffect(() => {
        axios.get(`/api/v1/products/product/${productId}/product`).then(response => setProduct(response.data));
    }, [productId]);

    return (
        <div>
            <h2>{product.name}</h2>
            <p>{product.description}</p>
            <span>{product.price}</span>
        </div>
    );
};

export default ProductDetails;
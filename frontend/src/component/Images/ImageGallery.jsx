// Images
// ImageGallery.jsx
import React, { useState } from 'react';
import axios from 'axios';

const ImageGallery = () => {
    const [images, setImages] = useState([]);
    const [file, setFile] = useState(null);

    const fetchImages = async () => {
        const response = await axios.get('/api/v1/images/all');
        setImages(response.data);
    };

    const handleUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);
        await axios.post('/api/v1/images/upload', formData);
        await fetchImages();
    };

    return (
        <div>
            <input type="file" onChange={(e) => setFile(e.target.files[0])} />
            <button onClick={handleUpload}>Upload</button>
            <div>
                {images.map(image => (
                    <img key={image.id} src={`/api/v1/images/image/download/${image.id}`} alt="Uploaded" />
                ))}
            </div>
        </div>
    );
};

export default ImageGallery;
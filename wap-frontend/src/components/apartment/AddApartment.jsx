import React, { useState } from 'react'
import {addApartment} from "../utils/ApiFunctions"


function AddApartment() {

    const [newApartment, setNewApartment] = useState({//new Apartments initial states
        apartmentType: "",           // Apartment type (e.g., Studio, 1 Bedroom)
        description: "",              // Description of the apartment
        pricePerNight: 0,             // Price per night
        size: 0,                      // Size of the apartment (e.g., in square meters)
        numberOfBedrooms: 0,          // Number of bedrooms
        numberOfBathrooms: 0,         // Number of bathrooms
        availabilityStatus: false ,       // Availability status (e.g., Available, Booked)
        photo: null
    });

    //Some initial states
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [imagePreview, setImagePreview] = useState("");

    //This function updates the state of newApartment based on user input.
    const handleApartmentInputChange = (e) => {
        const name = e.target.name;
        let value = e.target.value;

    // Validation for numeric fields, checks if the input is a number; if not, it sets the value to an empty string
        if (["pricePerNight", "size", "numberOfBedrooms", "numberOfBathrooms"].includes(name)) {
            if (!isNaN(value)) {
                value = parseInt(value);
            } else {
                value = ""; // Set to empty string if not a number
            }
        }
        // Convert availabilityStatus to boolean
/*         if (name === "availabilityStatus") {
            value = value === "true"; // Convert string "true"/"false" to actual boolean
        } */
        setNewApartment({ ...newApartment, [name]: value });
    };

    //This function manages the image file selection.
    const handleImageChange = (e) => {
        const selectedImage = e.target.files[0];
        setNewApartment({ ...newApartment, photo: selectedImage });
        setImagePreview(URL.createObjectURL(selectedImage));
    }; 

    //This function is called when the form is submitted
    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevents the default form submission behavior
    
        try {
            const success = await addApartment(
                newApartment.apartmentType,
                newApartment.description,
                newApartment.pricePerNight,
                newApartment.size,
                newApartment.numberOfBedrooms,
                newApartment.numberOfBathrooms,
                newApartment.availabilityStatus,
                newApartment.photo
            );
    
            if (success !== undefined) {
                setSuccessMessage("A new apartment was added successfully!");
                
                setNewApartment({
                    apartmentType: "",
                    description: "",
                    pricePerNight: 0,
                    size: 0,
                    numberOfBedrooms: 0,
                    numberOfBathrooms: 0,
                    availabilityStatus: "",
                    photo: null
                });
                setImagePreview(null); // Set photo preview to null

            } else {
                setErrorMessage("Error adding new apartment");
            }
        } catch (error) {
            setErrorMessage(error.message);
        }
    
        setTimeout(() => {
            setSuccessMessage("");
            setErrorMessage("");
        }, 3000);
    };
    
    
    return (
        <section className="container mt-5 mb-5 ">
            <div className="d-flex justify-content-center">
                <div className="col-md-8 col-lg-6">
                    <h2 className="mt-5 mb-2 web-color">Add a New Apartment</h2>
                    <hr />
                    <form onSubmit={handleSubmit}> 

                        <div className="mb-3">
                            <label htmlFor="apartmentType" className="web-color">Apartment Type</label>
                            <input
                                className="form-control"
                                type="text"
                                id="apartmentType"
                                name="apartmentType" // Match this with the state field
                                value={newApartment.apartmentType}
                                onChange={handleApartmentInputChange}
                                required
                                
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="description" className="web-color">Description</label>
                            <textarea
                                id="description"
                                name="description"
                                className="form-control"
                                value={newApartment.description}
                                onChange={handleApartmentInputChange}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="pricePerNight" className="web-color">Price Per Night</label>
                            <input
                                type="number"
                                id="pricePerNight"
                                name="pricePerNight"
                                className="form-control"
                                value={newApartment.pricePerNight}
                                onChange={handleApartmentInputChange}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="size" className="web-color">Size (sq meters)</label>
                            <input
                                type="number"
                                id="size"
                                name="size"
                                className="form-control"
                                value={newApartment.size}
                                onChange={handleApartmentInputChange}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="numberOfBedrooms" className="web-color">Number of Bedrooms</label>
                            <input
                                type="number"
                                id="numberOfBedrooms"
                                name="numberOfBedrooms"
                                className="form-control"
                                value={newApartment.numberOfBedrooms}
                                onChange={handleApartmentInputChange}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="numberOfBathrooms" className="web-color">Number of Bathrooms</label>
                            <input
                                type="number"
                                id="numberOfBathrooms"
                                name="numberOfBathrooms"
                                className="form-control"
                                value={newApartment.numberOfBathrooms}
                                onChange={handleApartmentInputChange}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="availabilityStatus" className="web-color">Availability Status</label>
                            <select
                                id="availabilityStatus"
                                name="availabilityStatus"
                                //className="form-control"
                                value={newApartment.availabilityStatus}  // Bind to state
                                onChange={handleApartmentInputChange}    // Update state on change
                                required
                            >
                                <option value="false">Unavailable</option>
                                <option value="true">Available</option>
                            </select>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="photo" className="web-color">Photo</label>
                            <input
                                type="file"
                                id="photo"
                                name="photo"
                                className="form-control-file"
                                onChange={handleImageChange}
                            />
                         {imagePreview && <img src={imagePreview} alt="Preview" style={{ maxHeight: '200px' }} />}
                        </div> 

                        <div className="text-center ">
                            <button type="submit" className="btn btn-web" >
                                Add Apartment
                            </button>
                            {successMessage && <p className="text-success mt-3">{successMessage}</p>}
                            {errorMessage && <p className="text-danger mt-3">{errorMessage}</p>}
                        </div>
                    </form>
                </div>
            </div>
        </section>
    );
}

export default AddApartment
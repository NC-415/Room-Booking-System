import axios from "axios";

export const api = axios.create({
    baseURL : " http://localhost:8080"
})

// Function to get the authorization header
/* export const getHeader = () => {
	const token = localStorage.getItem("token")
	return {
		Authorization: `Bearer ${token}`,
		"Content-Type": "application/json"
	}
}
 */
// ApiFunctions.js
export async function addApartment(apartmentType, description, pricePerNight, size, numberOfBedrooms, numberOfBathrooms, availabilityStatus, photo) {
    try {
        const formData = new FormData();
        formData.append('apartmentType', apartmentType);
        formData.append('description', description);
        formData.append('pricePerNight', pricePerNight);
        formData.append('size', size);
        formData.append('numberOfBedrooms', numberOfBedrooms);
        formData.append('numberOfBathrooms', numberOfBathrooms);
        formData.append('availabilityStatus', availabilityStatus);
        formData.append('photo', photo);

        const response = await api.post('/apartments', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });

        if (response.status === 200) {
            return true;
        } else {
            return false;
        }
    } catch (error) {
        console.error("Error adding apartment:", error);
        throw error;
    }
}

/* This function gets all rooms from the database */
export async function getAllApartments() {
	try {
		const result = await api.get('/apartments')
		return result.data
	} catch (error) {
		throw new Error("Error fetching apartments")
	}
}


/* This function deletes an apartment from the database */
export async function deleteApartment(apartmentId) {
    try {
        const result = await api.delete(`/apartments/${apartmentId}`) // use backticks here
        return result.data
    } catch (error) {
        throw new Error(`Error deleting apartment: ${error.message}`)
    }
}

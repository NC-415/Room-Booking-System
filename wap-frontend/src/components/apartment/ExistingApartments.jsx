import React, { useEffect, useState } from 'react'
import ApartmentFilter from '../common/ApartmentFilter'
import { deleteApartment, getAllApartments } from "../utils/ApiFunctions"
import { FaTrashAlt, FaEdit, FaEye, FaPlus } from "react-icons/fa"
import { Link } from "react-router-dom"
import ApartmentPaginator from '../common/ApartmentPaginator'
import { Row, Col, Modal, Button } from 'react-bootstrap'

function ExistingApartments() {
	// State variables
	const [apartments, setApartments] = useState([{ apartmentId: "", apartmentType: "", pricePerNight: "" }]) // Holds all apartments
	const [currentPage, setCurrentPage] = useState(1) // Current page number
	const [apartmentsPerPage] = useState(8) // Apartments displayed per page
	const [isLoading, setIsLoading] = useState(false) // Loading state
	const [filteredApartments, setFilteredApartments] = useState([{ apartmentId: "", apartmentType: "", pricePerNight: "" }]) // Filtered apartments based on selected type
	const [selectedApartmentType, setSelectedApartmentType] = useState("") // Selected apartment type for filtering
	const [errorMessage, setErrorMessage] = useState("") // Error message for operations
	const [successMessage, setSuccessMessage] = useState("") // Success message for operations
	const [showDeleteModal, setShowDeleteModal] = useState(false) // Controls delete confirmation modal visibility
	const [apartmentToDelete, setApartmentToDelete] = useState(null) // Stores the ID of the apartment to be deleted

	// Fetch apartments when component mounts
	useEffect(() => {
		fetchApartments()
	}, [])

	// Fetch all apartments from API
	const fetchApartments = async () => {
		setIsLoading(true)
		try {
			const result = await getAllApartments() // Get apartments from API
			setApartments(result) // Set retrieved apartments to state
			setIsLoading(false)
		} catch (error) {
			setErrorMessage(error.message) // Set error message if fetching fails
			setIsLoading(false)
		}
	}

	// Filter apartments based on selected apartment type
	useEffect(() => {
		if (selectedApartmentType === "") {
			setFilteredApartments(apartments) // Show all apartments if no type selected
		} else {
			const filteredApartments = apartments.filter((apartment) => apartment.apartmentType === selectedApartmentType) // Filter by type
			setFilteredApartments(filteredApartments)
		}
		setCurrentPage(1) // Reset to first page after filtering
	}, [apartments, selectedApartmentType])

	// Update page number for pagination
	const handlePaginationClick = (pageNumber) => {
		setCurrentPage(pageNumber)
	}

	// Show delete confirmation modal
	const handleDeleteClick = (apartmentId) => {
		setApartmentToDelete(apartmentId) // Set selected apartment for deletion
		setShowDeleteModal(true) // Show confirmation modal
	}

	// Confirm deletion of apartment
	const confirmDelete = async () => {
		if (apartmentToDelete) {
			try {
				await deleteApartment(apartmentToDelete) // Delete apartment via API
				setSuccessMessage(`Apartment No ${apartmentToDelete} was deleted successfully`) // Show success message
				fetchApartments() // Refresh apartment list after deletion
			} catch (error) {
				setErrorMessage(error.message) // Show error message if deletion fails
			} finally {
				setShowDeleteModal(false) // Hide modal
				setApartmentToDelete(null) // Clear selected apartment
				setTimeout(() => {
					setSuccessMessage("")
					setErrorMessage("")
				}, 3000) // Clear messages after 3 seconds
			}
		}
	}

	// Calculate apartments for current page
	const indexOfLastApartment = currentPage * apartmentsPerPage
	const indexOfFirstApartment = indexOfLastApartment - apartmentsPerPage
	const currentApartments = filteredApartments.slice(indexOfFirstApartment, indexOfLastApartment) // Apartments for current page

	return (
		<>
			{/* Messages */}
			<div className="container col-md-8 col-lg-6">
				{successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}
				{errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
			</div>

			{/* Apartment list or loading message */}
			{isLoading ? (
				<p>Loading existing apartments</p>
			) : (
				<>
					{/* Apartment table with filter and add button */}
					<section className="mt-5 mb-5 container">
						<div className="d-flex justify-content-between mb-3 mt-5">
							<h2>Existing apartments</h2>
						</div>

						<Row>
							<Col md={6} className="mb-2 md-mb-0">
								{/* Filter apartments by type */}
								<ApartmentFilter data={apartments} setFilteredData={setFilteredApartments} />
							</Col>
							<Col md={6} className="d-flex justify-content-end">
								<Link to={"/add-apartment"}>
									<FaPlus /> Add Apartment {/* Link to add a new apartment */}
								</Link>
							</Col>
						</Row>

						{/* Apartment details table */}
						<table className="table table-bordered table-hover">
							<thead>
								<tr className="text-center">
									<th>ID</th>
									<th>Apartment Type</th>
									<th>Apartment Price</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								{currentApartments.map((apartment) => (
									<tr key={apartment.apartmentId} className="text-center">
										<td>{apartment.apartmentId}</td>
										<td>{apartment.apartmentType}</td>
										<td>{apartment.pricePerNight}</td>
										<td className="gap-2">
											<Link to={`/edit-apartment/${apartment.apartmentId}`} className="gap-2">
												<span className="btn btn-info btn-sm">
													<FaEye /> {/* View icon */}
												</span>
												<span className="btn btn-warning btn-sm ml-5">
													<FaEdit /> {/* Edit icon */}
												</span>
											</Link>
											{/* Delete button opens confirmation modal */}
											<button
												className="btn btn-danger btn-sm ml-5"
												onClick={() => handleDeleteClick(apartment.apartmentId)}>
												<FaTrashAlt /> {/* Delete icon */}
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>

						{/* Pagination component */}
						<ApartmentPaginator
							currentPage={currentPage}
							totalPages={Math.ceil(filteredApartments.length / apartmentsPerPage)}
							onPageChange={handlePaginationClick}
						/>
					</section>
				</>
			)}

			{/* Delete Confirmation Modal */}
			<Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
				<Modal.Header closeButton>
					<Modal.Title>Confirm Deletion</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					Are you sure you want to delete Apartment No {apartmentToDelete}?
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
						Cancel {/* Cancel deletion */}
					</Button>
					<Button variant="danger" onClick={confirmDelete}>
						Delete {/* Confirm deletion */}
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	)
}

export default ExistingApartments

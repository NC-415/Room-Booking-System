import React, { useState } from "react"


const ApartmentFilter = ({ data, setFilteredData }) => {
	const [filter, setFilter] = useState("")

	const handleSelectChange = (e) => {
		const selectedType = e.target.value
		setFilter(selectedType)

		const filteredApartments = data.filter((apartment) =>
			apartment.apartmentType.toLowerCase().includes(selectedType.toLowerCase())
		)
		setFilteredData(filteredApartments)
	}

	const clearFilter = () => {
		setFilter("")
		setFilteredData(data)
	}

	const apartmentTypes = ["", ...new Set(data.map((apartment) => apartment.apartmentType))]

	return (
		<div className="input-group mb-3">
			<span className="input-group-text" id="room-type-filter">
				FIlter apartments by type
			</span>
			<select
				className="form-select"
				aria-label="romm type filter"
				value={filter}
				onChange={handleSelectChange}>
				<option value="">select a Apartment type to filter....</option>
				{apartmentTypes.map((type, index) => (
					<option key={index} value={String(type)}>
						{String(type)}
					</option>
				))}
			</select>
			<button className="btn btn-hotel" type="button" onClick={clearFilter}>
				Clear Filter
			</button>
		</div>
	)
}
export default ApartmentFilter

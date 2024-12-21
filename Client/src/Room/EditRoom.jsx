import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const EditRoom = () => {
    const { state: room } = useLocation(); // Get room data from navigation state
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        roomType: '',
        roomId: '',
        roomNumber: '',
        bedQuantity: '',
        description: '',
        status: 'AVAILABLE',
    });

    // Populate form data when room data is available
    useEffect(() => {
        console.log(room)
        if (room) {
            setFormData({
                roomType: room["Quantity of bed"] || '',
                roomId: room["Quantity of bed"] || '',
                roomNumber: room.roomNumber || '',
                bedQuantity: room.bedQuantity || '',
                description: room.description || '',
                status: room.status || 'AVAILABLE',
            });
        }
        console.log(formData)
    }, [room]);



    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleUpdateRoom = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/api/room/updateRoom`, formData, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
                },
            });
            alert('Room updated successfully');
            navigate(-1); // Go back to the previous page
        } catch (error) {
            console.error('Failed to update room', error);
            alert('Failed to update room');
        }
    };

    return (
        <div className="p-4 max-w-2xl mx-auto">
            <h1 className="text-2xl font-bold mb-4 text-center">Edit Room</h1>
            <form onSubmit={handleUpdateRoom} className="space-y-4">
                <div>
                    <label className="block font-medium">Room Type</label>
                    <input
                        type="text"
                        name="roomType"
                        value={formData.roomType}
                        onChange={handleInputChange}
                        className="w-full border border-gray-300 px-4 py-2 rounded-lg"
                        required
                    />
                </div>
                <div>
                    <label className="block font-medium">Room Number</label>
                    <input
                        type="text"
                        name="roomNumber"
                        value={formData.roomNumber}
                        onChange={handleInputChange}
                        className="w-full border border-gray-300 px-4 py-2 rounded-lg"
                        required
                    />
                </div>
                <div>
                    <label className="block font-medium">Bed Quantity</label>
                    <input
                        type="number"
                        name="bedQuantity"
                        value={formData.bedQuantity}
                        onChange={handleInputChange}
                        className="w-full border border-gray-300 px-4 py-2 rounded-lg"
                        required
                    />
                </div>
                <div>
                    <label className="block font-medium">Description</label>
                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleInputChange}
                        className="w-full border border-gray-300 px-4 py-2 rounded-lg"
                        required
                    ></textarea>
                </div>
                <div>
                    <label className="block font-medium">Status</label>
                    <select
                        name="status"
                        value={formData.status}
                        onChange={handleInputChange}
                        className="w-full border border-gray-300 px-4 py-2 rounded-lg"
                        required
                    >
                        <option value="AVAILABLE">Available</option>
                        <option value="BOOKED">Booked</option>
                    </select>
                </div>
                <div className="flex justify-end space-x-4">
                    <button
                        type="button"
                        onClick={() => navigate(-1)}
                        className="bg-gray-300 text-black px-4 py-2 rounded-lg hover:bg-gray-400"
                    >
                        Cancel
                    </button>
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
                    >
                        Update
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditRoom;
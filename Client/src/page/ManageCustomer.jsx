import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const ManageCustomer = () =>{
     const [currentPage, setCurrentPage] = useState(0);
      const [listUserAccount, setListUserAccount] = useState(null);
      const [isLoading, setIsLoading] = useState(false);
      const [error, setError] = useState(null);
      const pageSize = 10;
      const navigate = useNavigate();       
    
    
      useEffect(() => {
        const fetchData = async () => {
          setIsLoading(true);
          setError(null);
          try {
            const response = await fetch(
              `http://localhost:8080/api/userAccount/getAll?page=${currentPage}&size=${pageSize}`,
              {
                method: "GET",
                headers: {
                  Authorization: `Bearer ${localStorage.getItem("jwt")}`,
                },
              }
            );
    
            if (!response.ok) {
              throw new Error("Không thể tải dữ liệu");
            }
    
            const data = await response.json();
            setListUserAccount(data);
          } catch (error) {
            setError(error.message);
          } finally {
            setIsLoading(false);
          }
        };
    
        fetchData();
      }, [currentPage]);
    
      if (isLoading) {
        return <div className="text-center p-6">Loading Data...</div>;
      }
    
      if (error) {
        return (
          <div className="text-center p-6 text-red-500">
            Đã xảy ra lỗi: {error}
          </div>
        );
      }
    
      if (!listUserAccount) {
        return <div className="text-center p-6">No data.</div>;
      }
    
      const handleViewDetail = (id) =>{
        navigate(`/CustomerDetail/${id}`)
      }
    
      return (
        <div className="min-h-screen bg-gray-100 p-6">
          <div className="max-w-6xl mx-auto bg-white shadow-lg rounded-lg">
            <header className="p-4 border-b border-gray-200">
              <h1 className="text-2xl font-bold text-gray-700">List Account</h1>
            </header>
            <div className="p-4">
              {listUserAccount.length === 0 ? (
                <div className="text-center text-gray-500">
                  No booking.
                </div>
              ) : (
                <table className="table-auto w-full text-sm text-left">
                  <thead>
                    <tr className="bg-gray-200 text-gray-600 uppercase text-xs">
                      <th className="px-4 py-2">ID</th>
                      <th className="px-4 py-2">User Name</th>
                      <th className="px-4 py-2">Role</th>
                    </tr>
                  </thead>
                  <tbody>
                    {listUserAccount.map((account, index) => (
                      <tr
                        key={index}
                        className="border-b border-gray-200 hover:bg-gray-50"
                      >
                        <td className="px-4 py-2">{account.id}</td>
                        <td className="px-4 py-2">{account.userName}</td>
                        <td className="px-4 py-2">{account.roleName}</td>
                        <td className="px-4 py-2">
                          <button
                            className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600"
                            onClick={() => handleViewDetail(account.id)}
                          >
                            View Detail
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </div>
            
          </div>
        </div>
      );
}
export default ManageCustomer;
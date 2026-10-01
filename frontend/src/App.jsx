import { BrowserRouter, Routes, Route } from 'react-router-dom'
import DatasetIndex from './pages/DatasetIndex.jsx'
import DatasetDetail from './pages/DatasetDetail.jsx'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<DatasetIndex />} />
        <Route path="/datasets/:slug" element={<DatasetDetail />} />
      </Routes>
    </BrowserRouter>
  )
}

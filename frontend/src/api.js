const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api'

export async function fetchDatasets() {
  const res = await fetch(`${API_BASE}/datasets`)
  if (!res.ok) throw new Error('Failed to load datasets')
  return res.json()
}

export async function fetchDataset(slug) {
  const res = await fetch(`${API_BASE}/datasets/${slug}`)
  if (!res.ok) throw new Error('Dataset not found')
  return res.json()
}
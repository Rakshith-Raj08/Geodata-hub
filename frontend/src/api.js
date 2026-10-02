export async function fetchDatasets() {
  const res = await fetch('/datasets.json')
  if (!res.ok) throw new Error('Failed to load datasets')
  return res.json()
}

export async function fetchDataset(slug) {
  const all = await fetchDatasets()
  const found = all.find((d) => d.slug === slug)
  if (!found) throw new Error('Dataset not found')
  return found
}
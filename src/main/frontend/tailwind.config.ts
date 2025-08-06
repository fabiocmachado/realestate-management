import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#1e3a8a',
          dark: '#1e40af',
          light: '#3b82f6',
        },
        gray: {
          light: '#f9fafb',
          DEFAULT: '#374151',
          medium: '#6b7280',
          dark: '#1f2937',
          pale: '#d1d5db',
        },
      },
    },
  },
  plugins: [],
}

export default config
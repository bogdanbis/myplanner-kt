import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

import { defineConfig, loadEnv } from 'vite'

export default defineConfig(({ mode }) => {
	const env = loadEnv(mode, process.cwd())
	return {
		plugins: [
			vue({
				template: {
					compilerOptions: {
						isCustomElement: (tag) => tag === 'circle-progress',
					},
				},
			}),
		],
		resolve: {
			alias: {
				'@': fileURLToPath(new URL('./src', import.meta.url)),
			},
		},
		build: {
			sourcemap: false,
			outDir: env.VITE_OUT_DIR,
		},
	}
})

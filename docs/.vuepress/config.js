module.exports = {
  title: 'SignColors',
  description: 'Write with colors and formats on signs.',
  themeConfig: {
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Guide', link: '/guide/' },
      { text: 'Commands & Permissions', link: '/cmdandperms/' },
      { text: 'Spigot', link: 'https://www.spigotmc.org/resources/signcolors.6135/' },
      { text: 'GitHub', link: 'https://github.com/Pixelhash/SignColors' }
    ],
    locales: {
      '/': {

      },
      '/de/': {
        selectText: 'Sprachen',
        nav: [
          { text: 'Startseite', link: '/' },
          { text: 'Guide', link: '/guide/' },
          { text: 'Befehle & Rechte', link: '/cmdandperms/' },
          { text: 'Spigot', link: 'https://www.spigotmc.org/resources/signcolors.6135/' },
          { text: 'GitHub', link: 'https://github.com/Pixelhash/SignColors' }
        ]
      }
    }
  },
  head: [
    ['link', { rel: 'apple-touch-icon', sizes: '60x60', href: '/apple-touch-icon.png' }],
    ['link', { rel: 'icon', type: 'image/png', sizes: '32x32', href: '/favicon-32x32.png' }],
    ['link', { rel: 'icon', type: 'image/png', sizes: '16x16', href: '/favicon-16x16.png' }],
    ['link', { rel: 'manifest', href: '/site.webmanifest' }],
    ['link', { rel: 'mask-icon', href: '/safari-pinned-tab.svg', color: '#5bbad5' }],
    ['meta', { rel: 'msapplication-TileColor', content: '#ffc40d' }],
    ['meta', { rel: 'theme-color', content: '#ffffff' }],
  ],
  locales: {
    '/': {
      lang: 'en-US',
    },
    '/de/': {
      lang: 'de-DE',
      title: 'SignColors',
      description: 'Schreibe mit Farben und Formaten auf Schildern.'
    }
  }
}
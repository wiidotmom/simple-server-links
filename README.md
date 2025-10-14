# Simple Server Links

Easily configure the Server Links button introduced in 1.21 for your server

Make sure to comment out `bug-report-link` in your `server.properties`

## Demo Video
[![](https://github.com/malloryhayr/simple-server-links/assets/22878174/523fac02-636f-4a22-aadc-050a8cff3c6d)](https://files.farlands.cafe/media_attachments/files/112/527/712/368/000/196/original/baf7f42f11cd32e3.mp4)

## Example config
```ini
known_server_link.website=https://mc.example.com
custom.server_link.wiki=https://wiki.mc.example.com
Rules=https://mc.example.com/rules
```

## Generic server link types
| Translation Key                        | Display Name         |
| -------------------------------------- | -------------------- |
| known_server_link.announcements        | Announcements        |
| known_server_link.community            | Community            |
| known_server_link.community_guidelines | Community Guidelines |
| known_server_link.feedback             | Feedback             |
| known_server_link.forums               | Forums               |
| known_server_link.news                 | News                 |
| known_server_link.report_bug           | Report Server Bug*  |
| known_server_link.status               | Status               |
| known_server_link.support              | Support              |
| known_server_link.website              | Website              |

*Also shows if the player encounters a crash.

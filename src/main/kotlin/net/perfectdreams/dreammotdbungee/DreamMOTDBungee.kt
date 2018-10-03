package net.perfectdreams.dreammotdbungee

import net.md_5.bungee.api.Favicon
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import net.perfectdreams.dreamcorebungee.utils.DreamUtils
import net.perfectdreams.dreamcorebungee.utils.TextUtils
import net.perfectdreams.dreamcorebungee.utils.extensions.toTextComponent
import net.perfectdreams.dreammotdbungee.commands.DreamMOTDBungeeCommand
import java.io.File
import javax.imageio.ImageIO

class DreamMOTDBungee : Plugin(), Listener {
	val favicons = mutableListOf<Favicon>()

	override fun onEnable() {
		super.onEnable()
		this.proxy.pluginManager.registerListener(this, this)
		DreamMOTDBungeeCommand(this).register(this)
		loadFavicons()
	}

	@EventHandler
	fun onProxyPing(e: ProxyPingEvent) {
		// val version = ProtocolSupportAPI.getProtocolVersion(e.connection.address)

		val online = this.proxy.players.size
		val max = this.proxy.players.size + 1

		e.response.players.online = online
		e.response.players.max = max

		// if (version.protocolType == ProtocolType.PE) {
		// 	e.response.descriptionComponent = "§6(ﾉ◕ヮ◕)ﾉ §e* :･ﾟ✧ §b§lPerfect§3§lDreams §6^-^ §7- §fEntre agora!".toTextComponent()
		// } else {
		/* if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			// roll eyes...
			// MOTD para as versões mais velhas do Minecraft
			e.response.descriptionComponent = "§5§l»§d§l» §b§lPerfect§3§lDreams §d§l«§5§l« §6^-^                         §7Transformando sonhos em realidade!".toTextComponent()
		} else { */
		val top = TextUtils.getCenteredMessage("§a\u266b §6(\uff89\u25d5\u30ee\u25d5)\uff89 §e* :\uff65\uff9f\u2727 §4§lSparkly§b§lPower §e\u2727\uff9f\uff65: *§6\u30fd(\u25d5\u30ee\u25d5\u30fd) §a\u266b", 128)
		var bottom = TextUtils.getCenteredMessage("§5§l»§d§l» §fModéstia a parte, esse servidor é incrível! §d§l«§5§l«", 128)
		e.response.descriptionComponent = "$top\n$bottom".toTextComponent()
		e.response.version.protocol = 999

		// TODO: Algo ao fazer hover
		e.response.version.name = "§3➦ §bEntre agora! §e${online}§6/§e${max}"

		if (favicons.isNotEmpty()) {
			e.response.setFavicon(favicons[DreamUtils.random.nextInt(favicons.size)])
		}
		//	}
		// }
	}

	fun loadFavicons() {
		favicons.clear()
		File(dataFolder, "server-icons").listFiles().filter { it.extension == "png" } .forEach {
			this.logger.info("Loading ${it.name}...")
			val icon = ImageIO.read(it)
			favicons.add(Favicon.create(icon))
		}
	}
}
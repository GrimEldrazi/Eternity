package net.grimm.eternity.common.registry;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.data.DebugMode;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataAttachments {

    public static final DeferredRegister<AttachmentType<?>> TYPES = DeferredRegister.create(
            NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Eternity.MOD_ID);

    public static final Supplier<AttachmentType<DebugMode>> DEBUG_ATTACHMENT = TYPES.register("debug",
            () -> AttachmentType.serializable(DebugMode::new).copyOnDeath().build());

}
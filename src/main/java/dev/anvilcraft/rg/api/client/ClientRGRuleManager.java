package dev.anvilcraft.rg.api.client;

import dev.anvilcraft.rg.RollingGate;
import dev.anvilcraft.rg.api.RGEnvironment;
import dev.anvilcraft.rg.api.RGRuleManager;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.progress.ProgressMeter;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.lang.annotation.ElementType;

/**
 * ClientRGRuleManager类是RGRuleManager的子类，专门用于客户端环境
 * 它通过继承RGRuleManager类并指定环境为客户端，来管理客户端特定的规则
 */
public class ClientRGRuleManager extends RGRuleManager {

    public static final String ANNOTATION_NAME = "L" + RGClientRules.class.getName().replace(".", "/") + ";";

    /**
     * 初始化ClientRGRuleManager对象，设置命名空间和环境
     *
     * @param namespace 命名空间，用于区分不同的规则管理器实例
     */
    public ClientRGRuleManager(String namespace) {
        super(namespace, RGEnvironment.CLIENT);
    }

    public void compileContent() throws ClassNotFoundException {
        ProgressMeter meter = StartupNotificationManager.addProgressBar("Load Server Rules", LoadingModList.get().getModFiles().size());
        for (ModFileInfo modFile : LoadingModList.get().getModFiles()) {
            meter.increment();
            @SuppressWarnings("UnstableApiUsage")
            ModFileScanData scanData = modFile.getFile().getScanResult();
            for (ModFileScanData.AnnotationData annotation : scanData.getAnnotations()) {
                if (annotation.annotationType().getDescriptor().equals(ANNOTATION_NAME) && annotation.targetType() == ElementType.TYPE) {
                    String memberName = annotation.memberName();
                    Class<?> clazz = Class.forName(memberName);
                    String modId = (String) annotation.annotationData().get("value");
                    if (modId == null) modId = RollingGate.MODID;
                    this.register(clazz, modId);
                }
            }
        }
        StartupNotificationManager.popBar(meter);
    }
}


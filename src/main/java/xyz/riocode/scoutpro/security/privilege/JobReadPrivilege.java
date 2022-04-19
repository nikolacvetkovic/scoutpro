package xyz.riocode.scoutpro.security.privilege;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('job.read')")
public @interface JobReadPrivilege {
}

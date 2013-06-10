package org.jboss.tools.cdi.bot.test.creator.config;

/**
 * 
 * @author jjankovi
 *
 */
public class ScopeConfiguration extends CDIComponentConfiguration {

	private boolean inherited;
	private boolean normalScope;
	private boolean passivating;

	@Override
	public ScopeConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public ScopeConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public ScopeConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isInherited() {
		return inherited;
	}

	public ScopeConfiguration setInherited(boolean inherited) {
		this.inherited = inherited;
		return this;
	}

	public boolean isNormalScope() {
		return normalScope;
	}

	public ScopeConfiguration setNormalScope(boolean normalScope) {
		this.normalScope = normalScope;
		return this;
	}

	public boolean isPassivating() {
		return passivating;
	}

	public ScopeConfiguration setPassivating(boolean passivating) {
		this.passivating = passivating;
		return this;
	}

	@Override
	public ScopeConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
